package com.wanwh.springcloudlearningadmin.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONString;
import cn.hutool.json.JSONUtil;
import com.wanwh.springcloudlearningadmin.util.HttpRequest;
import com.wanwh.springcloudlearningadmin.util.MD5Util;
import com.wanwh.springcloudlearningadmin.util.wx.Util;
import io.micrometer.core.instrument.util.JsonUtils;
import lombok.Data;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.notify.AbstractStatusChangeNotifier;
import reactor.core.publisher.Mono;



/**
 * Created by wanwh on 2019/8/26 0026.
 */
@Data
public class MyNotifier extends AbstractStatusChangeNotifier {

    private static final String DEFAULT_MESSAGE = "*#{instance.registration.name}* (#{instance.id}) is *#{event.statusInfo.status}**";

    private final SpelExpressionParser parser = new SpelExpressionParser();
    private RestTemplate restTemplate = new RestTemplate();
    private String wxOpenIds;
    private String atMobiles;
    private String msgtype = "markdown";
    private String title = "服务告警";
    private Expression message;
    private String url = "http://127.0.0.1:8084/xlzx/api/sendWeChatInfo.action";
    private String templateId = "jr6Ktbvw4zmMNPCKrLxtbXQ3rwI7QpVX0ClXhwQ81pE";
    private String salt = "salt";

    public MyNotifier(InstanceRepository repository) {
        super(repository);
        this.message = parser.parseExpression(DEFAULT_MESSAGE, ParserContext.TEMPLATE_EXPRESSION);
    }

    private String getMessage(InstanceEvent event,Instance instance) {
        Map<String, Object> root = new HashMap<>(3);
        root.put("event", event);
        root.put("instance", instance);
        root.put("lastStatus", getLastStatus(event.getInstance()));
        StandardEvaluationContext context = new StandardEvaluationContext(root);
        context.addPropertyAccessor(new MapAccessor());
        return message.getValue(context, String.class);
    }

    private String getAtMobilesString(String s) {
        StringBuilder atMobiles = new StringBuilder();
        String[] mobiles = s.split(",");
        for (String mobile : mobiles) {
            atMobiles.append("@").append(mobile);
        }
        return atMobiles.toString();
    }

    private HttpEntity<String> createMessage(InstanceEvent event, Instance instance) {
        HttpHeaders headers = new HttpHeaders();
        //一定要设置好ContentType为utf8否则会乱码
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        Map<String, Object> messageJson = new HashMap<>(4);
        List<Map<String,String>> openIdList = new ArrayList<Map<String,String>>(1);
        openIdList.add(new HashMap<String, String>(){
            {
                put("weixin_id", wxOpenIds);
            }
        });
        messageJson.put("openId", JSONUtil.toJsonStr(openIdList));
        messageJson.put("content", Util.getWeChatTemplate("服务监控狗巡检","","","",""));
        messageJson.put("templateId",templateId);
        messageJson.put("tokenId",MD5Util.md5Hex(templateId + "2018zktj"));
        return new HttpEntity(messageJson, headers);
    }

    private String creatParams(InstanceEvent event, Instance instance){
        Map<String, Object> messageJson = new HashMap<>(4);
        List<Map<String,String>> openIdList = new ArrayList<Map<String,String>>(1);
        openIdList.add(new HashMap<String, String>(){
            {
                put("weixin_id", wxOpenIds);
            }
        });
        String parmas = "templateId=" + templateId +
                "&content=" + JSONUtil.toJsonStr(Util.getWeChatTemplate("服务监控狗巡检","2","3",instance.getInfo().getValues().get("project-name")+"",instance.getStatusInfo().getDetails().get("message") + "")) +
                "&openId=" + JSONUtil.toJsonStr(openIdList) +
                "&tokenId=" + MD5Util.md5Hex(templateId + salt);
        return parmas;
    }

    @Override
    protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {
        return Mono.fromRunnable(() -> HttpRequest.sendPost(url, creatParams(event,instance)));
    }
}

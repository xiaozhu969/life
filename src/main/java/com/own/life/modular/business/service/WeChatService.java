package com.own.life.modular.business.service;

import com.alibaba.fastjson.JSONObject;
import com.own.life.core.common.constant.WechatConstants;
import com.own.life.core.util.AccessTokenThread;
import com.own.life.core.util.WeixinUtil;
import com.own.life.modular.business.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class WeChatService {

    public String processRequest(HttpServletRequest request) {
        // xml格式的消息数据
        String respXml = null;
        // 默认返回的文本消息内容
        String respContent;
        try {
            // 调用parseXml方法解析请求消息
            Map<String,String> requestMap = WeixinUtil.parseXml(request);
            log.info("requestMap:{}",requestMap);
            // 消息类型
            String msgType = (String) requestMap.get(WechatConstants.MsgType);
            String mes = null;
            // 文本消息
            if (msgType.equals(WechatConstants.REQ_MESSAGE_TYPE_TEXT)) {
                log.info("文本消息");
                mes =requestMap.get(WechatConstants.Content).toString();
                if(mes!=null&&mes.length()<2){
                    List<ArticleItem> items = new ArrayList<>();
                    ArticleItem item = new ArticleItem();
                    item.setTitle("照片墙");
                    item.setDescription("阿狸照片墙");
                    item.setPicUrl("http://changhaiwx.pagekite.me/photo-wall/a/iali11.jpg");
                    item.setUrl("http://changhaiwx.pagekite.me/page/photowall");
                    items.add(item);

                    item = new ArticleItem();
                    item.setTitle("哈哈");
                    item.setDescription("一张照片");
                    item.setPicUrl("http://changhaiwx.pagekite.me/images/me.jpg");
                    item.setUrl("http://changhaiwx.pagekite.me/page/index");
                    items.add(item);

                    item = new ArticleItem();
                    item.setTitle("小游戏2048");
                    item.setDescription("小游戏2048");
                    item.setPicUrl("http://changhaiwx.pagekite.me/images/2048.jpg");
                    item.setUrl("http://changhaiwx.pagekite.me/page/game2048");
                    items.add(item);

                    item = new ArticleItem();
                    item.setTitle("百度");
                    item.setDescription("百度一下");
                    item.setPicUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505100912368&di=69c2ba796aa2afd9a4608e213bf695fb&imgtype=0&src=http%3A%2F%2Ftx.haiqq.com%2Fuploads%2Fallimg%2F170510%2F0634355517-9.jpg");
                    item.setUrl("http://www.baidu.com");
                    items.add(item);

                    respXml = WeixinUtil.sendArticleMsg(requestMap, items);
                }
//                else if("我的信息".equals(mes)){
//                    Map<String, String> userInfo = getUserInfo(requestMap.get(WechatConstants.FromUserName));
//                    System.out.println(userInfo.toString());
//                    String nickname = userInfo.get("nickname");
//                    String city = userInfo.get("city");
//                    String province = userInfo.get("province");
//                    String country = userInfo.get("country");
//                    String headimgurl = userInfo.get("headimgurl");
//                    List<ArticleItem> items = new ArrayList<>();
//                    ArticleItem item = new ArticleItem();
//                    item.setTitle("你的信息");
//                    item.setDescription("昵称:"+nickname+" 地址:"+country+" "+province+" "+city);
//                    item.setPicUrl(headimgurl);
//                    item.setUrl("http://www.baidu.com");
//                    items.add(item);
//
//                    respXml = WeixinUtil.sendArticleMsg(requestMap, items);
//                }
            }
            // 图片消息
            else if (msgType.equals(WechatConstants.REQ_MESSAGE_TYPE_IMAGE)) {
                log.info("图片消息");
                respContent = "您发送的是图片消息！";
                respXml = WeixinUtil.sendTextMsg(requestMap, respContent);
            }
            // 语音消息
            else if (msgType.equals(WechatConstants.REQ_MESSAGE_TYPE_VOICE)) {
                log.info("语音消息");
                respContent = "您发送的是语音消息！";
                respXml = WeixinUtil.sendTextMsg(requestMap, respContent);
            }
            // 视频消息
            else if (msgType.equals(WechatConstants.REQ_MESSAGE_TYPE_VIDEO)) {
                log.info("视频消息");
                respContent = "您发送的是视频消息！";
                respXml = WeixinUtil.sendTextMsg(requestMap, respContent);
            }
            // 地理位置消息
            else if (msgType.equals(WechatConstants.REQ_MESSAGE_TYPE_LOCATION)) {
                log.info("地理位置消息");
                respContent = "您发送的是地理位置消息！";
                respXml = WeixinUtil.sendTextMsg(requestMap, respContent);
            }
            // 链接消息
            else if (msgType.equals(WechatConstants.REQ_MESSAGE_TYPE_LINK)) {
                log.info("链接消息");
                respContent = "您发送的是链接消息！";
                respXml = WeixinUtil.sendTextMsg(requestMap, respContent);
            }
            // 事件推送
            else if (msgType.equals(WechatConstants.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = (String) requestMap.get(WechatConstants.Event);
                // 关注
                if (eventType.equals(WechatConstants.EVENT_TYPE_SUBSCRIBE)) {
                    log.info("谢谢您的关注");
                    respContent = "谢谢您的关注！";
                    respXml = WeixinUtil.sendTextMsg(requestMap, respContent);
                }
                // 取消关注
                else if (eventType.equals(WechatConstants.EVENT_TYPE_UNSUBSCRIBE)) {
                    // TODO 取消订阅后用户不会再收到公众账号发送的消息，因此不需要回复
                }
                // 扫描带参数二维码
                else if (eventType.equals(WechatConstants.EVENT_TYPE_SCAN)) {
                    // TODO 处理扫描带参数二维码事件
                }
                // 上报地理位置
                else if (eventType.equals(WechatConstants.EVENT_TYPE_LOCATION)) {
                    // TODO 处理上报地理位置事件
                }
                // 自定义菜单
                else if (eventType.equals(WechatConstants.EVENT_TYPE_CLICK)) {
                    // TODO 处理菜单点击事件
                }
            }
            mes = mes == null ? "不知道你在干嘛" : mes;
            if(respXml == null)
                respXml = WeixinUtil.sendTextMsg(requestMap, mes);
            log.info("respXml:{}",respXml);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            respXml = null;
        }
        return respXml;
    }

    /**
     * 添加自定义菜单
     */
    public boolean menuAdd() {
        String url = WechatConstants.MENU_CREATE_URL.replace("ACCESS_TOKEN", AccessTokenThread.accessToken.getToken());
        String menu = JSONObject.toJSONString(initMenu());
        log.info("menu:{}",menu);
        JSONObject result = WeixinUtil.httpRequest(url , "POST", menu);
        if("ok".equals(result.getString("errmsg"))){
            log.info("添加菜单结果：{}", result);
            return true;
        }
        log.info("添加菜单结果：{}", result);
        return false;
    }

    /**
     * 组装菜单
     * @return
     */
    public Menu initMenu(){
        Menu menu = new Menu();
        ViewButton button11 = new ViewButton();
        //注意按钮名字不要太长，不然会报40018错误
        button11.setName("搜索");
        button11.setType("view");
        button11.setUrl("https://www.baidu.com");

        ClickButton button31 = new ClickButton();
        button31.setName("点赞");
        button31.setType("click");
        button31.setKey("strtest");//事件key

        Button button = new Button();
        button.setName("click2");
        button.setSub_button(new Button[]{button31});
        button.setSub_button(new Button[]{button31});

        menu.setButton(new Button[]{button11,button});
        return menu;
    }
}

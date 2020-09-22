/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.own.life.modular.business.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import com.own.life.core.util.CheckUtil;
import com.own.life.modular.business.service.WeChatService;
import com.own.life.modular.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * wx控制器
 *
 * @author fengshuonan
 * @Date 2017年1月10日 下午8:25:24
 */
@RestController
@Slf4j
@RequestMapping("/weChat")
public class WeChatController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private WeChatService weChatService;

    /**
     *处理微信服务器发来的get请求，进行签名的验证
     */
    @GetMapping("/access")
    public String login(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
            return echostr;
        }else{
            return null;
        }
    }

    /**
     * 此处是处理微信服务器的消息转发的
     * @param request
     */
    @PostMapping("/access")
    public String access(HttpServletRequest request) {
        return weChatService.processRequest(request);
    }

    /**
     * 添加微信公众号菜单
     * @return
     */
    @RequestMapping(value="/menuAdd",method=RequestMethod.POST)
    public String menuAdd(){
        boolean b = weChatService.menuAdd();
        if (b) {
            return "success";
        }
        return "unsuccess";
    }
}

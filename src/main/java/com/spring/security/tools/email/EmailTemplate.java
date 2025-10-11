package com.spring.security.tools.email;

public class EmailTemplate {

    
    public static String getEmailTemplate(String url){
        String html = "";
        html = " <style type='text/css'> " 
                + ".email{ width:100%; max-width:640px; margin:0 auto;}" 
                + ".email .header{ padding:0 25px; height:100px; color:#fff;}" 
                + ".email .header span{ display:inline-block; height:24px; margin-top:35px; line-height:24px; float:left; font-size:16px;}" 
                + ".email .header div{ display:inline-block; vertical-align:middle; padding-right:10px; margin-right:10px; border-right:1px solid #fff; float:left; margin-top:35px;}" 
                + ".email .content{padding:35px 25px; font-size:16px;}" 
                + ".email .content h4{ font-size:16px; color:#4A4A4A; font-weight:normal; padding-bottom:20px;}" 
                + ".email .content p{ padding:10px 35px; line-height:26px; word-break:break-all;}" 
                + ".email .content p a.btn{ display:inline-block; padding:4px 15px; background-color:#4db6ac; color:#fff; text-decoration:none; margin-bottom:15px;}" 
                + ".email .content p span{ color:#4db6ac;}" 
                + ".email .content p em{ font-size:14px; font-style:normal;}" 
                + ".email .content div{ text-align:right; font-size:14px; margin-top:50px;}" 
                + ".email .footer{ padding:0 25px; background-color:#F6F6F6; color:#979797; font-size:14px;}" 
                + ".email .footer p{ padding:20px 0; line-height:20px;}" 
                + ".email .footer div{ height:100px;}" 
                + ".email .footer div img{ width:100px; height:100px; float:left;}" 
                + ".email .footer div span{ float:left; margin:30px 0 0 10px; line-height:20px;}" 
                + "</style>"
                + "<div class='email'>" 
                + "    <div class='header'>" 
                + "      <div><img src='cid:pay_strand' style='width: 148px'></div>" 
                + "    </div>" 
                + "" 
                + "    <div class='content'>" 
                + "      <h4>点击下面链接以完成立即注册</h4>" 
                + "" 
                + "      <p><a class='btn' href='"+url+"'" 
                + "        target='_blank'>立即注册</a><br>" 
                + "      <a href='"+url+" '" 
                + "        target='_blank'> "+url+" " 
                + "      </a></p>" 
                + "  </div>" 
                + "  <div class='footer'>" 
                + "    <p>如果您并未发过此请求，可能是因为其他用户在验证邮箱时误输入了您的邮箱地址而使你收到了 这封邮件，请忽略此封邮件，无需进行任何操作。</p>"
                + "    <div><img src='cid:pay_qr'><span>微信扫一扫，关注  pay 公众号</span>" 
                + "    </div>" 
                + " "
                + "    <p style='padding:5px 0; line-height:24px'>如有任何问题，请与我们联系，我们将尽快为你解答。<br>" 
                + "      Email: <span><a href='mailto:pay_service@361.com' target='_blank'>pay_service@361.com</a></span>&nbsp;&nbsp;"
                + "      电话: <span>13319576394</span></p>" 
                + "  </div>" 
                + "  </div>" ;
        
        return html;
    }
    
}

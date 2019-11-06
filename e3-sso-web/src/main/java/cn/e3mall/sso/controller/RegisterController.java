package cn.e3mall.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.RegisterService;

/*
 * 用户注册
 * 
 */
@Controller
public class RegisterController {

	@Autowired
	private RegisterService registerService;

	@RequestMapping("page/register")

	public String registerUser() {
		return "register";
	}

	// 注册有两次来发送ajax请求，第一次是对数据库的查找，看用户名或者密码是否被人注册过
	@RequestMapping("/user/check/{path}/{type}")
	@ResponseBody
	public E3Result checkData(@PathVariable String path, @PathVariable Integer type) {
		E3Result e3Result = registerService.checkData(path, type);
		return e3Result;
	}

	// 上面的验证通过，开始注册用户
	@RequestMapping("user/register")
	@ResponseBody
	public E3Result registerUser(TbUser tbUser) {
		E3Result e3Result = registerService.registerUser(tbUser);
		return e3Result;
	}
}

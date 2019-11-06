package cn.e3mall.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.mapper.TbUserMapper;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.pojo.TbUserExample.Criteria;
import cn.e3mall.sso.service.RegisterService;

/*
 * 用户注册
 * 
 */
@Service
public class RegisterServiceImpl implements RegisterService {
	@Autowired
	private TbUserMapper userMapper;

	public E3Result checkData(String path, Integer type) {
		// 根据不同的type生成不同的查询条件
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		// type----》1-用户名 2-电话 3-邮箱
		if (type == 1) {
			criteria.andUsernameEqualTo(path);
		} else if (type == 2) {
			criteria.andPhoneEqualTo(path);
		} else if (type == 3) {
			criteria.andEmailEqualTo(path);
		} else {
			return E3Result.build(400, "数据错误");
		}
		List<TbUser> list = userMapper.selectByExample(example);
		// 如果查到数据库有人注册过这些
		if (list != null && list.size() > 0) {
			return E3Result.ok(false);
		}
		// 数据库没有数据，说明没有人注册过这些
		return E3Result.ok(true);
	}

	@Override
	public E3Result registerUser(TbUser tbUser) {
		// 检验数据
		if (StringUtils.isBlank(tbUser.getUsername()) || StringUtils.isBlank(tbUser.getPassword())
				|| StringUtils.isBlank(tbUser.getPhone())) {
			return E3Result.build(400, "数据不完整，从新注册");
		}

		// 1：用户名 2：手机号 3：邮箱
		E3Result result = checkData(tbUser.getUsername(), 1);
		if (!(boolean) result.getData()) {
			return E3Result.build(400, "此用户名已经被占用");
		}
		result = checkData(tbUser.getPhone(), 2);
		if (!(boolean) result.getData()) {
			return E3Result.build(400, "手机号已经被占用");
		}

		// 开始向数据库插入数据，密码是md5加密
		tbUser.setCreated(new Date());
		tbUser.setUpdated(new Date());
		// 密码加密
		String md5Pass = DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes());
		tbUser.setPassword(md5Pass);
		int insert = userMapper.insert(tbUser);
		return E3Result.ok();
	}
}

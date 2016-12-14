package com.sns.biz.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sns.biz.member.MemberDAO;
import com.sns.biz.vo.Role;
import com.sns.biz.vo.User;

@Service("customUserDetailService")
public class CustomUserDetailService implements UserDetailsService{
	
	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailService.class);

	@Resource private MemberDAO memberDao;
	
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		
		logger.info("loadUserByUsername :" + userId);
		
		HashMap<String,Object> userMap = memberDao.selectSecurityValue(userId); 
		
		//request를 사용 할 수있는방법이지만 올바른 방법이 아님.
		//ServletContext servletContext = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getServletContext();
		
		
		logger.info("loadUserByUsername userMap: "+userMap.toString());
		
		
		if(userMap == null){
			throw new UsernameNotFoundException("userName NotFound");

		}else{
			logger.info("userName Is True");
		}
		
		User user = new User();
		user.setUsername(userMap.get("userId").toString());
		user.setPassword(userMap.get("userPass").toString());
		
		// 권한 부여 
		Role role = new Role();
		role.setName("ROLE_USER");
		
		List<Role> roles = new ArrayList<Role>();
		roles.add(role);
		user.setAuthorities(roles);
		
		return user;
	}

}

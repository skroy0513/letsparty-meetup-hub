package com.letsparty.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.letsparty.service.PartyService;
import com.letsparty.service.UserPartyApplicationService;
import com.letsparty.service.UserService;
import com.letsparty.vo.Party;
import com.letsparty.vo.User;
import com.letsparty.vo.UserPartyApplication;

@Component
public class PartyInterceptor implements HandlerInterceptor {

	private final PartyService partyService;
	private final UserService userService;
	private final UserPartyApplicationService userPartyApplicationService;
	@Value("${s3.path.covers}")
	private String coversPath;
	
	@Autowired
	public PartyInterceptor(PartyService partyService, UserService userService, UserPartyApplicationService userPartyApplicationService) {
		this.partyService = partyService;
		this.userService = userService;
		this.userPartyApplicationService = userPartyApplicationService;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		String uri = request.getRequestURI();
		int partyNo = Integer.parseInt(uri.split("/")[2]);
		Party party = partyService.getPartyByNo(partyNo);
		party.setFilename(coversPath + party.getFilename());
		
		System.out.println(partyNo);
		System.out.println(party.getLeader().getId());
		
		UserPartyApplication userPartyApplication = userPartyApplicationService.findByPartyNoAndUserId(partyNo, party.getLeader().getId());
		System.out.println(userPartyApplication.toString());
		modelAndView.addObject("party", party);
		modelAndView.addObject("user", userPartyApplication);
	}
}

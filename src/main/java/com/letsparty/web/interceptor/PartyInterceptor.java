package com.letsparty.web.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.letsparty.dto.ChatRoomWithUsers;
import com.letsparty.dto.ChatUserResponse;
import com.letsparty.security.user.LoginUser;
import com.letsparty.service.ChatService;
import com.letsparty.service.PartyService;
import com.letsparty.service.UserPartyApplicationService;
import com.letsparty.vo.Party;
import com.letsparty.vo.UserPartyApplication;

@Component
public class PartyInterceptor implements HandlerInterceptor {

	private final PartyService partyService;
	private final UserPartyApplicationService userPartyApplicationService;
	private final ChatService chatService;
	@Value("${s3.path.covers}")
	private String coversPath;
	@Value("${s3.path.profiles}")
	private String profilePath;
	
	@Autowired
	public PartyInterceptor(PartyService partyService,  UserPartyApplicationService userPartyApplicationService, ChatService chatService) {
		this.partyService = partyService;
		this.userPartyApplicationService = userPartyApplicationService;
		this.chatService = chatService;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		
		String uri = request.getRequestURI();
		int partyNo = Integer.parseInt(uri.split("/")[2]);
		
		Party party = partyService.getPartyByNo(partyNo);
		party.setFilename(coversPath + party.getFilename());
		
		// 로그인을 하고 해당 파티에 가입된 경우엔 right.html과 채팅방 정보를 가져온다.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
			LoginUser user = (LoginUser) authentication.getPrincipal();
			System.out.println(user.getName());
			System.out.println(user.getNo());
			
			// 내가 가입한 파티의 채팅방 목록 불러오기(내가 참여중인 방만)
			List<ChatRoomWithUsers> chatRooms = chatService.getChatRoomByPartyNoAndUserId(partyNo, user.getNo());
			for (ChatRoomWithUsers chatroom : chatRooms) {
				for (ChatUserResponse chatUser : chatroom.getChatUsers()) {
					if (chatUser.isUrl()) {
						chatUser.setFilename(profilePath + chatUser.getFilename());
					}
				}
			}
			System.out.println(chatRooms.size());
			modelAndView.addObject("chatRooms", chatRooms);
		}
		
		UserPartyApplication partyOfLeader = userPartyApplicationService.findByPartyNoAndUserId(partyNo, party.getLeader().getId());
		
		int memberCnt = userPartyApplicationService.countPartyMemberWithStatus(partyNo, "승인");
		
		modelAndView.addObject("party", party);
		modelAndView.addObject("leader", partyOfLeader);
		modelAndView.addObject("memberCnt", memberCnt);
		modelAndView.addObject("partyNo", partyNo);
	}
}

package com.letsparty.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.letsparty.vo.Poll;
import com.letsparty.vo.PollAnswer;
import com.letsparty.vo.PollOption;

@Mapper
public interface PollMapper {
	
	void insertPoll(Poll poll);
	
	void insertPollOption(PollOption pollOption);

	Poll getPollByPostId(long postId);

	List<PollOption> getPollOptionsByPollNo(int pollNo);

	void insertPollAnswer(@Param("userId") String userId, @Param("optionPk") int optionPk);

	PollOption getPollOptionByOptionPk(int optionPk);

	void updateOption(PollOption savedOption);

	PollAnswer getAnswerByOptionPk(int optionPk);

//	void updateAnswerByUserIdAndPk(@Param("userId") String userId, @Param("optionPk") int optionPk);

	PollAnswer getAnswerByUserIdAndOptionPk(@Param("userId") String userId, @Param("optionPk") int optionPk);

	void deletePollAnswer(@Param("userId") String userId, @Param("optionPk") int optionPk);
}

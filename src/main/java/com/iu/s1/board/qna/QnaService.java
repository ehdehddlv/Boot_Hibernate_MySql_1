package com.iu.s1.board.qna;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional(rollbackOn = Exception.class)
public class QnaService {

	@Autowired
	private QnaRepository qnaRepository;
	
	public QnaVO boardInsert(QnaVO qnaVO) throws Exception{
		//원본글
		//ref = 자기자신의 글번호
		//step, depth = 0
		//ref에 자기 자신의 num을 넣어주고 update하고 리턴
		qnaVO.setStep(0L);
		qnaVO.setDepth(0L);
		qnaVO = qnaRepository.save(qnaVO);
		qnaVO.setRef(qnaVO.getNum());
		return qnaRepository.save(qnaVO);
	}
	
	public Page<QnaVO> boardList(Pageable pageable, String search, String kind) throws Exception{

		Page<QnaVO> ar = null;		
		
		if(kind.equals("title")) {
			ar = qnaRepository.findByTitleContaining(search, pageable);
		}else if(kind.equals("writer")) {
			ar = qnaRepository.findByWriterContaining(search, pageable);
		}else {
			ar = qnaRepository.findByContentsContaining(search, pageable);
		}
		
		return ar;
	}
}

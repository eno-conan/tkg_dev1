package com.eno.controller;

import java.util.List;

import org.hibernate.TransientPropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.eno.entity.StudentSubject;
import com.eno.entity.master.TimeTableNormal;
import com.eno.service.TkgService1;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class TkgController1 {

	@Autowired
	private TkgService1 tkgService1;

	@GetMapping("/tkg")
	public String memberFormView(Model model) {
		/* 教室：新規登録 */
//		tkgService1.registNewClassroom();
		/* 社員？：室長登録 */
//		tkgService1.registClassroomDirector();
		/* 講師：新規登録 */
//		tkgService1.registLecturer();
		/* 講師：定期コマ登録 */
//		try {
//			tkgService1.decideLecturerWorkableTimeNormal();
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			return "error/500";
//		}
		/* 講師：指導科目設定 */
//		tkgService1.decideLecturerTeachSubjects();
		/* 社員：承認（承認対象は後で・・・） */
		/* 講師：授業実績登録 */
		/* 講師：授業実績漏れ移動 */
		/* 講師：講習期間スケジュール登録 */
		/* 講師：担当生徒スケジュール登録 */
		/* 講師：給与明細表示（新たにテーブル作成が必要） */
		/* 生徒：ログイン */
		/* 生徒：新規登録 */
		/* 生徒：受講科目登録（担当講師設定） */
		/* 生徒：授業振替 */
		/* 生徒：講習期間スケジュール提出 */
		return "dummy";
	}
	// 全取得系
	// tkgService1.findAllMGrade();
	// tkgService1.findAllMArea();
	// tkgService1.findAllClassroom();
	// tkgService1.findAllLecturer();

}

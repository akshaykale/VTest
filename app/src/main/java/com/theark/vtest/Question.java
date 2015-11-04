package com.theark.vtest;

public class Question {
	
	String question,option_a,option_b,option_c,option_d,answer,s_ans;
	
	public Question() {
		question = "";
		option_a = "";
		option_b = "";
		option_c = "";
		option_d = "";
		answer = "";
		s_ans = "a";
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getOption_a() {
		return option_a;
	}

	public void setOption_a(String option_a) {
		this.option_a = option_a;
	}

	public String getOption_b() {
		return option_b;
	}

	public void setOption_b(String option_b) {
		this.option_b = option_b;
	}

	public String getOption_c() {
		return option_c;
	}

	public void setOption_c(String option_c) {
		this.option_c = option_c;
	}

	public String getOption_d() {
		return option_d;
	}

	public void setOption_d(String option_d) {
		this.option_d = option_d;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getS_ans() {
		return s_ans;
	}

	public void setS_ans(String s_ans) {
		this.s_ans = s_ans;
	}
	
	
}

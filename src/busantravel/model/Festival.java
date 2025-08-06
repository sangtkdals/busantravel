package busantravel.model;

public class Festival {
	private String f_id; //축제 ID
	private String f_place; //장소명
	private String f_intro; //소개문
	private String f_num; // 연락처
	private String f_tra; //교통 정보
	private String f_infor; //운영 정보
	private String f_addr; //주소
	
	
	
	public Festival(String f_id, String f_place, String f_intro, String f_num, String f_tra, String f_infor,
			String f_addr) {
		this.f_id = f_id;
		this.f_place = f_place;
		this.f_intro = f_intro;
		this.f_num = f_num;
		this.f_tra = f_tra;
		this.f_infor = f_infor;
		this.f_addr = f_addr;
	}
	
	public String getF_id() {
		return f_id;
	}
	public void setF_id(String f_id) {
		this.f_id = f_id;
	}
	public String getF_place() {
		return f_place;
	}
	public void setF_place(String f_place) {
		this.f_place = f_place;
	}
	public String getF_intro() {
		return f_intro;
	}
	public void setF_intro(String f_intro) {
		this.f_intro = f_intro;
	}
	public String getF_num() {
		return f_num;
	}
	public void setF_num(String f_num) {
		this.f_num = f_num;
	}
	public String getF_tra() {
		return f_tra;
	}
	public void setF_tra(String f_tra) {
		this.f_tra = f_tra;
	}
	public String getF_infor() {
		return f_infor;
	}
	public void setF_infor(String f_infor) {
		this.f_infor = f_infor;
	}
	public String getF_addr() {
		return f_addr;
	}
	public void setF_addr(String f_addr) {
		this.f_addr = f_addr;
	}
	
	
}

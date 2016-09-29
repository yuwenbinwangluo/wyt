package baseframe.net;


public class ResultValue {
      private int code;
      private String message="";
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
    public ResultValue(int code,String message){
    	this.code=code;
    	this.message=message;
    }  
    public void autoSetCodeAndMessage(int code){
    	this.code=code;
    	this.message=ReturnValue.getStrCode(code);
    }  
}

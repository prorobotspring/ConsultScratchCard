package ve.com.digitel.scratch.to;

public class CardDataTO {
	
	private String  subscriberId;
	private String  rechargeDateTime;
	private String  Exception;

	
	public String getSubscriberId() {
		return subscriberId;
	}
	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}
	public String getRechargeDateTime() {
		return rechargeDateTime;
	}
	public void setRechargeDateTime(String rechargeDateTime) {
		this.rechargeDateTime = rechargeDateTime;
	}
	public String getException() {
		return Exception;
	}
	public void setException(String exception) {
		Exception = exception;
	}

}

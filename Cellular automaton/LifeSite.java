
//lifesite class will represent a cell in the matrix.
public class LifeSite {

	private boolean status;
	private boolean updateStatus;
	
	public LifeSite() {
		this.status = false;
		this.updateStatus = false;
	}
	
	public boolean getSiteStatus() {
		return this.status;
	}
	
	public boolean getUpdateStatus() {
		return this.updateStatus;
	}
	
	public void setSiteStatus(boolean newStatus) {
		this.status = newStatus;
	}
	
	public void setUpdateStatus(boolean newUpdate) {
		this.updateStatus = newUpdate;
	}
}

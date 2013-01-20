package master;

public class ConnectedUser {
	
	private int userId;
	private String computerName;
	private boolean inUse;
	private boolean master;
	
	public ConnectedUser(int userId,String computerName,boolean inUse,boolean master){
		this.userId = userId;
		this.computerName = computerName;
		this.inUse = inUse;
		this.master = master;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getComputerName() {
		return computerName;
	}

	public void setComputerName(String computerName) {
		this.computerName = computerName;
	}

	public boolean isInUse() {
		return inUse;
	}

	public void setInUse(boolean inUse) {
		this.inUse = inUse;
	}

	public boolean isMaster() {
		return master;
	}

	public void setMaster(boolean master) {
		this.master = master;
	}

}

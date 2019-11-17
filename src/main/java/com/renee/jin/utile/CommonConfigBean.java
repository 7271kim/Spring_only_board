package com.renee.jin.utile;

public class CommonConfigBean {
	private String development;
	private String allowImage;
	private String allowFile;
	private String memUrl;
	private String mainUrl;
	private String simsimUrl;
	private String allowFileSize;
	private String localSimSimSaveFolder;
	private String remoteSimSimSaveFolder;
	
	
	
	public String getRemoteSimSimSaveFolder() {
		return remoteSimSimSaveFolder;
	}
	public void setRemoteSimSimSaveFolder(String remoteSimSimSaveFolder) {
		this.remoteSimSimSaveFolder = remoteSimSimSaveFolder;
	}
	public String getAllowFileSize() {
        return allowFileSize;
    }
    public void setAllowFileSize(String allowFileSize) {
        this.allowFileSize = allowFileSize;
    }
    public String getLocalSimSimSaveFolder() {
        return localSimSimSaveFolder;
    }
    public void setLocalSimSimSaveFolder(String localSimSimSaveFolder) {
        this.localSimSimSaveFolder = localSimSimSaveFolder;
    }
    public String getSimsimUrl() {
        return simsimUrl;
    }
    public void setSimsimUrl(String simsimUrl) {
        this.simsimUrl = simsimUrl;
    }
    public String getDevelopment() {
		return development;
	}
	public void setDevelopment(String development) {
		this.development = development;
	}
	public String getAllowImage() {
		return allowImage;
	}
	public void setAllowImage(String allowImage) {
		this.allowImage = allowImage;
	}
	public String getAllowFile() {
		return allowFile;
	}
	public void setAllowFile(String allowFile) {
		this.allowFile = allowFile;
	}
    public String getMemUrl() {
        return memUrl;
    }
    public void setMemUrl(String memUrl) {
        this.memUrl = memUrl;
    }
    public String getMainUrl() {
        return mainUrl;
    }
    public void setMainUrl(String mainUrl) {
        this.mainUrl = mainUrl;
    }
	
	
}

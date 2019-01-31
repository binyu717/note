package util.email;

/**
 *
 * 邮件配置
 */
public class EmailSMTPConfig {

    private String smtp;
    private String userName;
    private boolean isSSL;
    private Integer port;
    private String user;
    private String password;

    @Override
    public String toString() {
        return "EmailConfig{" +
                "smtp='" + smtp + '\'' +
                ", userName='" + userName + '\'' +
                ", isSSL=" + isSSL +
                ", port=" + port +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSmtp() {
        return smtp;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    public boolean isSSL() {
        return isSSL;
    }

    public void setSSL(boolean SSL) {
        isSSL = SSL;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

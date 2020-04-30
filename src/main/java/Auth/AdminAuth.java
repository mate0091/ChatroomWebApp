package Auth;

import com.sun.net.httpserver.BasicAuthenticator;

public class AdminAuth extends BasicAuthenticator
{
    public AdminAuth(String s) {
        super(s);
    }

    @Override
    public boolean checkCredentials(String s, String s1) {
        return s.equals("admin") && s1.equals("password");
    }
}

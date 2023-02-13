package app.messages;

import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

public class AuditingFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest req,
                         ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
        long start = new Date().getTime();
        chain.doFilter(req, res); // doFilter() 체인에 실행할 필터가 존재하면 추가 필터를 호출할 수 있게 함.
        long elapsed = new Date().getTime() - start;
        HttpServletRequest request = (HttpServletRequest) req;
        logger.debug("Request[uri=" + request.getRequestURI() + ", method=" + request.getMethod()
                + "] completed in " + elapsed + " ms");
    }
}

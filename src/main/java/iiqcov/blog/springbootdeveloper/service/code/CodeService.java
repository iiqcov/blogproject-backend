package iiqcov.blog.springbootdeveloper.service.code;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CodeService {

    @Value("${match-url}")
    private String matchURL;

    public boolean urlMatch(String url){
        return url.equals(matchURL);
    }
}

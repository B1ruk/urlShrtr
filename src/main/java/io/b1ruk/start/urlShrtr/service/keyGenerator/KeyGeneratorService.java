package io.b1ruk.start.urlShrtr.service.keyGenerator;

import com.google.common.io.BaseEncoding;
import io.seruco.encoding.base62.Base62;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class KeyGeneratorService {

    public String base62Encode(String url) {
        Base62 base62 = Base62.createInstance();
        byte[] encode = base62.encode(url.getBytes());
        return new String(encode).substring(0, 8);
    }
}

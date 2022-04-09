package com.rlirette.tools.rentalhelper.batchs.impl.sub;

import com.rlirette.tools.rentalhelper.exception.IcsContentException;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

@Component
public class UrlContentGetter {

    public String getContentFrom(String unparseUrl) {
        URL url = parse(unparseUrl);
        return getContent(url);
    }

    private URL parse(String url) {
        try {
            return new URL(url);
        }catch (MalformedURLException e){
            throw new IcsContentException(String.format("Can't convert %s to URL", url), e);
        }
    }

    private String getContent(URL url) {
        return new BuffReader(instantiateWith(url)).readLineByLine();
    }

    private InputStreamReader instantiateWith(URL url) {
        try {
            return new InputStreamReader(url.openStream());
        }catch (IOException e){
            throw new IcsContentException("Can't create inputStreamReader instance", e);
        }
    }

    private class BuffReader extends BufferedReader {

        public BuffReader(Reader in) {
            super(in);
        }

        public String readLineByLine(){
            return lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }


}

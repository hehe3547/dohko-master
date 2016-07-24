package com.dohko.core.web;

import com.dohko.core.base.Holder;
import com.dohko.core.base.Response;
import com.dohko.core.base.ResultInfo;
import com.dohko.core.util.DataUtils;
import com.dohko.core.util.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.UUID;

public class ResultMessageConverter extends AbstractHttpMessageConverter<ResultInfo> {

	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	private Logger logger = LoggerFactory.getLogger(ResultMessageConverter.class);

	public ResultMessageConverter() {
		super(new MediaType("text", "html", DEFAULT_CHARSET), new MediaType("application", "json", DEFAULT_CHARSET));
	}
	
	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		return false;
	}
	
	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		return true;
	}
	

	@Override
	protected boolean supports(Class<?> clazz) {
		return Response.class.isAssignableFrom(clazz);
	}


	@Override
	protected ResultInfo readInternal(Class<? extends ResultInfo> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		String requestData = StreamUtils.copyToString(inputMessage.getBody(), DEFAULT_CHARSET);
		System.out.print(requestData);
		return null;
	}

	@Override
	protected void writeInternal(ResultInfo resultInfo, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		HttpServletRequest httpRequest = getHttpRequest();
		Holder holder = (Holder)httpRequest.getAttribute(Holder.Key.HOLDER.getKey());
		Response response = toResponse(resultInfo, holder);
		String jsondata = DataUtils.response2json(response);
		LogUtils.info(jsondata, logger);
		//String newjson = URLEncoder.encode(jsondata, "UTF-8");
		outputMessage.getHeaders().setContentType(new MediaType("application", "json", DEFAULT_CHARSET));
		StreamUtils.copy(jsondata, DEFAULT_CHARSET, outputMessage.getBody());
        //outputMessage.getBody().write(jsondata.getBytes());
	}

	private Response toResponse(ResultInfo resultInfo, Holder holder) {
		if (holder != null) {
			return Response.Builder.create(holder.getValue(Holder.Key.TRANCEID), resultInfo);
		}
		return Response.Builder.create(UUID.randomUUID().toString(), resultInfo);
	}

	private HttpServletRequest getHttpRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

}

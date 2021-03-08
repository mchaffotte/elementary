package fr.chaffotm.gamebook.elementary.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.nio.file.NoSuchFileException;

@Provider
public class NoSuchFileExceptionMapper implements ExceptionMapper<NoSuchFileException> {

    private static final Logger LOGGER = LoggerFactory.getLogger("Exceptions");

    @Override
    public Response toResponse(final NoSuchFileException e) {
        LOGGER.warn("NoSuchFileException occurs: ", e);
        return Response.status(Response.Status.NOT_FOUND).build();
    }

}


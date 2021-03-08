package fr.chaffotm.gamebook.elementary.resource;

import fr.chaffotm.gamebook.elementary.service.ImageService;

import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;

@Path("stories")
public class ImageResource {

    private final ImageService imageService;

    public ImageResource(final ImageService imageService) {
        this.imageService = imageService;
    }

    @GET
    @Path("{storyId}/images/{imageName}")
    @Produces("image/png")
    public Response get(@PathParam("storyId") final long storyId, @PathParam("imageName") final String imageName) throws IOException {
        final InputStream stream = imageService.getImageStream(storyId, imageName);
        if (stream == null) {
            throw new NotFoundException("The story " + storyId + " does not contain the image: '" + imageName + "'");
        }
        final Response.ResponseBuilder responseBuilder = Response.ok(stream);
        responseBuilder.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imageName +"\"");
        return responseBuilder.build();
    }
}

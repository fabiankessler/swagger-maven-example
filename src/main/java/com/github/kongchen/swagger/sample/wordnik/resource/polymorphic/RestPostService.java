package com.github.kongchen.swagger.sample.wordnik.resource.polymorphic;

import com.google.common.base.Optional;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * This service uses a complex JSON input that comes in the http post body payload.
 * The result is complex also.
 */
@Path("/v1/development")
@Produces({"application/json"})
@Api(value = "/v1/development", description = "Operations for development.")
public class RestPostService {

    @GET
    @Path("/get")
    @ApiOperation(value = "Retrieves complex data from the server.",
            notes = "This is only half of what the POST operation does; it only returns data from the server to the client, but does not send any. It's convenient to be called in a web browser.",
            response = ComplexObject.class
    )
    @ApiResponses(value = { @ApiResponse(code = 200, message = "A hardcoded consistent ComplexObject.")})
    public Response get(
            @QueryParam(value = "apiKey") final String apiKey
    ) {
        ComplexObject result = new ComplexObject("theresultäöü", 15, 3.33d, true, ComplexObject.Color.RED, new Circle("blue", 5d), Optional.of("foobar"), Optional.<String>absent());
        return Response.ok().entity(result).build();
    }

    @POST
    @Path("/post")
    @Consumes({"application/json"})
    @ApiOperation(value = "Send various data to the server.",
            notes = "Ensures that all these data types correctly make it to the server and back. Marshalling of all these types works.",
            response = ComplexObject.class
    )
    @ApiResponses(value = { @ApiResponse(code = 200, message = "A ComplexObject based on the input.")})
    public Response post(
            @QueryParam(value = "apiKey") final String apiKey,
            @ApiParam(value = "The ComplexObject as input.", required = true) ComplexObject complexParam
    ) {
        ComplexObject result = new ComplexObject(
                complexParam.getString() + "-resultäöü",
                complexParam.getIntNumber() * 2,
                complexParam.getDoubleNumber() * 2,
                !complexParam.isYesOrNo(),
                complexParam.getColor(),
                new Circle("light" + complexParam.getGeometricalFigure().getColor(), 5d),
                Optional.of(complexParam.getOptional1().get() + "bar"),
                Optional.<String>absent()
        );
        return Response.ok().entity(result).build();
    }

}

package com.softserve.taf.services.placeholder.endpoints;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.softserve.taf.models.enums.HttpStatus;
import com.softserve.taf.models.placeholder.comment.CommentDto;
import com.softserve.taf.services.common.AbstractWebEndpoint;

/**
 * Represents an endpoint for managing comments using REST API.
 */
public class CommentEndpoint extends AbstractWebEndpoint {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String COMMENTS_END = "/comments";
    private static final String COMMENTS_RESOURCE_END = "/comments/{commentID}";

    /**
     * Constructs a CommentEndpoint with the given RequestSpecification.
     *
     * @param specification The RequestSpecification used for HTTP requests.
     */
    public CommentEndpoint(RequestSpecification specification) {
        super(specification);
    }

    /**
     * Creates a new comment.
     *
     * @param commentDto The CommentDto object containing comment details.
     * @return The created CommentDto object.
     */
    public CommentDto create(CommentDto commentDto) {
        return create(commentDto, HttpStatus.CREATED)
                .extract().as(CommentDto.class);
    }

    /**
     * Creates a new comment.
     *
     * @param commentDto The CommentDto object containing comment details.
     * @param status     The expected HTTP status of the response.
     * @return The ValidatableResponse object representing the response.
     */
    public ValidatableResponse create(CommentDto commentDto, HttpStatus status) {
        LOGGER.info("Create new Comment");
        return post(
                this.specification,
                COMMENTS_END,
                commentDto)
                .statusCode(status.getCode());
    }

    /**
     * Updates an existing comment by ID.
     *
     * @param id         The ID of the comment to be updated.
     * @param commentDto The CommentDto object containing updated comment details.
     * @return The updated CommentDto object.
     */
    public CommentDto update(int id, CommentDto commentDto) {
        return update(commentDto, id, HttpStatus.OK)
                .extract().as(CommentDto.class);
    }

    /**
     * Updates an existing comment by ID.
     *
     * @param commentDto The CommentDto object containing updated comment details.
     * @param id         The ID of the comment to be updated.
     * @param status     The expected HTTP status of the response.
     * @return The ValidatableResponse object representing the response.
     */
    public ValidatableResponse update(CommentDto commentDto, int id, HttpStatus status) {
        LOGGER.info("Update Comment by id [{}]", id);
        return put(
                this.specification,
                COMMENTS_RESOURCE_END,
                commentDto,
                id)
                .statusCode(status.getCode());
    }

    /**
     * Retrieves a comment by its ID.
     *
     * @param id The ID of the comment to retrieve.
     * @return The retrieved CommentDto object.
     */
    public CommentDto getById(int id) {
        return getById(id, HttpStatus.OK)
                .extract().as(CommentDto.class);
    }

    /**
     * Retrieves a comment by its ID.
     *
     * @param id     The ID of the comment to retrieve.
     * @param status The expected HTTP status of the response.
     * @return The ValidatableResponse object representing the response.
     */
    public ValidatableResponse getById(int id, HttpStatus status) {
        LOGGER.info("Get Comment by id [{}]", id);
        return get(
                this.specification,
                COMMENTS_RESOURCE_END,
                String.valueOf(id))
                .statusCode(status.getCode());
    }

    /**
     * Retrieves all comments.
     *
     * @return A list of CommentDto objects representing all comments.
     */
    public List<CommentDto> getAll() {
        return List.of(getAll(HttpStatus.OK).extract().as(CommentDto[].class));
    }

    /**
     * Retrieves all comments.
     *
     * @param status The expected HTTP status of the response.
     * @return The ValidatableResponse object representing the response.
     */
    public ValidatableResponse getAll(HttpStatus status) {
        LOGGER.info("Get all Comments");
        ValidatableResponse response = get(this.specification, COMMENTS_END);
        response.statusCode(status.getCode());
        return response;
    }
}

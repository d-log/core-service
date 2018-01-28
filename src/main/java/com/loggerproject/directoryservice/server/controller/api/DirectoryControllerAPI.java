package com.loggerproject.directoryservice.server.controller.api;

import com.loggerproject.directoryservice.server.controller.api.model.create.RequestCreateDirectoryModels;
import com.loggerproject.directoryservice.server.controller.api.model.create.ResponseCreateDirectoryModel;
import com.loggerproject.directoryservice.server.controller.api.model.create.ResponseCreateDirectoryModels;
import com.loggerproject.directoryservice.server.controller.api.model.delete.RequestDeleteDirectoryModels;
import com.loggerproject.directoryservice.server.controller.api.model.delete.ResponseDeleteDirectoryModels;
import com.loggerproject.directoryservice.server.controller.api.model.get.RequestGetDirectoryModels;
import com.loggerproject.directoryservice.server.controller.api.model.get.ResponseGetDirectoryModels;
import com.loggerproject.directoryservice.server.controller.api.model.update.RequestUpdateDirectoryModels;
import com.loggerproject.directoryservice.server.controller.api.model.update.ResponseUpdateDirectoryModel;
import com.loggerproject.directoryservice.server.controller.api.model.update.ResponseUpdateDirectoryModels;
import com.loggerproject.directoryservice.server.data.model.DirectoryModel;
import com.loggerproject.directoryservice.server.service.DirectoryService;
import com.loggerproject.microserviceglobalresource.pojo.controller.response.ResponseModel;
import com.loggerproject.tagservice.client.service.TagServiceClient;
import com.loggerproject.tagservice.server.data.model.TagModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
public class DirectoryControllerAPI {

	@Autowired
	DirectoryService directoryService;

	@Autowired
	TagServiceClient tagServiceClient;

	@PostMapping(value = "/create", produces = "application/json")
	public ResponseModel create(@RequestBody RequestCreateDirectoryModels request) {
		TagModel tagModel = tagServiceClient.findOne(request.getModels().get(0).getId());

		ResponseCreateDirectoryModels responseModel = new ResponseCreateDirectoryModels();
		Boolean success = true;

		List<ResponseCreateDirectoryModel> responses = new ArrayList<>();
		for (DirectoryModel modelToCreate : request.getModels()) {
			ResponseCreateDirectoryModel response = new ResponseCreateDirectoryModel();
			DirectoryModel createdModel = null;
			try {
				createdModel = directoryService.create(modelToCreate);
				response.setSuccess(true);
				response.setModel(createdModel);
			} catch (Exception e) {
				success = false;
				response.setSuccess(false);
				response.setErrorMessage(e.getMessage());
				response.setModel(createdModel);
			}

			responses.add(response);
		}

		responseModel.setResponses(responses);
		responseModel.setSuccess(success);
		responseModel.setErrorMessage(success ? null : "see individual error message(s)");

		return responseModel;
	}

	@PostMapping(value = "/update", produces = "application/json")
	public ResponseModel update(@RequestBody RequestUpdateDirectoryModels request) {
		ResponseUpdateDirectoryModels responseModel = new ResponseUpdateDirectoryModels();
		Boolean success = true;

		List<ResponseUpdateDirectoryModel> responses = new ArrayList<>();
		for (DirectoryModel modelToUpdate : request.getModels()) {
			ResponseUpdateDirectoryModel response = new ResponseUpdateDirectoryModel();
			DirectoryModel updatedModel = null;
			try {
				updatedModel = directoryService.update(modelToUpdate);
				response.setSuccess(true);
				response.setModel(updatedModel);
			} catch (Exception e) {
				success = false;
				response.setSuccess(false);
				response.setErrorMessage(e.getMessage());
				response.setModel(updatedModel);
			}

			responses.add(response);
		}

		responseModel.setResponses(responses);
		responseModel.setSuccess(success);
		responseModel.setErrorMessage(success ? null : "see individual error message(s)");

		return responseModel;
	}

	@PostMapping(value = "/get", produces = "application/json")
	public ResponseModel get(@RequestBody RequestGetDirectoryModels request) {
		ResponseGetDirectoryModels response = new ResponseGetDirectoryModels();
		Boolean success = true;

		List<DirectoryModel> models = new ArrayList<>();
		List<String> nonExistentIds = new ArrayList<>();
		for (String id : request.getIds()) {
			DirectoryModel model = directoryService.findOne(id);
			if (model == null) {
				success = false;
				nonExistentIds.add(id);
			} else {
				models.add(model);
			}
		}

		response.setModels(models);
		response.setNonExistentIDs(nonExistentIds);
		response.setSuccess(success);
		response.setErrorMessage(success ? null : "non existent directory id(s): " + nonExistentIds.toString());

		return response;
	}

	@PostMapping(value = "/delete", produces = "application/json")
	public ResponseModel delete(@RequestBody RequestDeleteDirectoryModels request) {
		ResponseDeleteDirectoryModels response = new ResponseDeleteDirectoryModels();
		Boolean success = true;

		List<DirectoryModel> models = new ArrayList<>();
		List<String> nonExistentIds = new ArrayList<>();
		for (String id : request.getIds()) {
			DirectoryModel model = directoryService.delete(id);
			if (model == null) {
				success = false;
				nonExistentIds.add(id);
			} else {
				models.add(model);
			}
		}

		response.setModels(models);
		response.setNonExistentIDs(nonExistentIds);
		response.setSuccess(success);
		response.setErrorMessage(success ? null : "non existent directory id(s): " + nonExistentIds.toString());

		return response;
	}
}

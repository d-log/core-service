package com.loggerproject.directoryservice.server.controller.api;

import com.loggerproject.directoryservice.server.controller.api.model.create.RequestCreateDirectoryModels;
import com.loggerproject.directoryservice.server.controller.api.model.create.ResponseCreateDirectoryModels;
import com.loggerproject.directoryservice.server.controller.api.model.delete.RequestDeleteDirectoryModels;
import com.loggerproject.directoryservice.server.controller.api.model.delete.ResponseDeleteDirectoryModels;
import com.loggerproject.directoryservice.server.controller.api.model.get.RequestGetDirectoryModels;
import com.loggerproject.directoryservice.server.controller.api.model.get.ResponseGetDirectoryModels;
import com.loggerproject.directoryservice.server.controller.api.model.update.RequestUpdateDirectoryModels;
import com.loggerproject.directoryservice.server.controller.api.model.update.ResponseUpdateDirectoryModels;
import com.loggerproject.directoryservice.server.data.model.DirectoryModel;
import com.loggerproject.directoryservice.server.service.DirectoryService;
import com.loggerproject.microserviceglobalresource.pojo.controller.response.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
public class DirectoryControllerAPI {

	@Autowired
	DirectoryService directoryService;

	@PostMapping(value = "/create", produces = "application/json")
	public ResponseModel create(@RequestBody RequestCreateDirectoryModels request) {
		ResponseCreateDirectoryModels response = new ResponseCreateDirectoryModels();

		try {
			List<DirectoryModel> createdDirectoryModels = request.getModels().stream().map(model -> directoryService.create(model)).collect(Collectors.toList());
			response.setCreatedModels(createdDirectoryModels);
			response.setSuccess(true);
		} catch (Exception e) {
			response.setErrorMessage(e.getMessage());
			response.setSuccess(false);
		}

		return response;
	}

	@PostMapping(value = "/update", produces = "application/json")
	public ResponseModel update(@RequestBody RequestUpdateDirectoryModels request) {
		ResponseUpdateDirectoryModels response = new ResponseUpdateDirectoryModels();
		
		try {
			List<DirectoryModel> updatedDirectoryModels = request.getModels().stream().map(model -> directoryService.update(model)).collect(Collectors.toList());
			response.setUpdatedModels(updatedDirectoryModels);
			response.setSuccess(true);
		} catch (Exception e) {
			response.setErrorMessage(e.getMessage());
			response.setSuccess(false);
		}

		return response;
	}

	@PostMapping(value = "/get", produces = "application/json")
	public ResponseModel get(@RequestBody RequestGetDirectoryModels request) {
		ResponseGetDirectoryModels response = new ResponseGetDirectoryModels();

		try {
			List<DirectoryModel> models = request.getIds().stream().map(modelID -> directoryService.getFindOne(modelID)).collect(Collectors.toList());
			response.setModels(models);
			response.setSuccess(true);
		} catch (Exception e) {
			response.setErrorMessage(e.getMessage());
			response.setSuccess(false);
		}

		return response;
	}

	@PostMapping(value = "/delete", produces = "application/json")
	public ResponseModel delete(@RequestBody RequestDeleteDirectoryModels request) {
		ResponseDeleteDirectoryModels response = new ResponseDeleteDirectoryModels();

		try {
			List<DirectoryModel> deletedDirectoryModels = request.getIds().stream().map(modelID -> directoryService.delete(modelID)).collect(Collectors.toList());
			response.setDeletedModels(deletedDirectoryModels);
			response.setSuccess(true);
		} catch (Exception e) {
			response.setErrorMessage(e.getMessage());
			response.setSuccess(false);
		}

		return response;
	}
}

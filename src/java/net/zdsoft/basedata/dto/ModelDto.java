package net.zdsoft.basedata.dto;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.basedata.entity.Model;

public class ModelDto extends BaseDto{
    private Model model;
    private List<ModelDto> subModelDtos = new ArrayList<ModelDto>();

    public void addModelDto(ModelDto model) {
        subModelDtos.add(model);
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public List<ModelDto> getSubModelDtos() {
        return subModelDtos;
    }
}

package net.zdsoft.basedata.dto;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.basedata.entity.SubSystem;


public class SubSystemDto extends BaseDto {

    private SubSystem subSystem;
    private List<SubSystemDto> subSystemDtos = new ArrayList<SubSystemDto>();
    private List<ModelDto> modelDtos = new ArrayList<ModelDto>();

    public void addSubSystemDto(SubSystemDto subSystemDto) {
        subSystemDtos.add(subSystemDto);
    }

    public void addModelDto(ModelDto model) {
        modelDtos.add(model);
    }

    public List<SubSystemDto> getSubSystemDtos() {
        return subSystemDtos;
    }

    public List<ModelDto> getModelDtos() {
        return modelDtos;
    }

    public void setSubSystem(SubSystem subSystem) {
        this.subSystem = subSystem;
    }

    public SubSystem getSubSystem() {
        return subSystem;
    }

}

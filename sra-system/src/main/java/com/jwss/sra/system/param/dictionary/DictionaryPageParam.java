package com.jwss.sra.system.param.dictionary;

import com.jwss.sra.system.vo.DictionaryVO;
import io.swagger.annotations.ApiModelProperty;
import org.sagacity.sqltoy.model.Page;

import java.io.Serializable;

/**
 * 字典分页参数
 * @author jwss
 * @date 2022-3-22
 */
public class DictionaryPageParam extends Page<DictionaryVO> implements Serializable {
    private static final long serialVersionUID = 8818887425379309640L;

    @ApiModelProperty("字典属性")
    private DictionaryVO dictionaryVO;

    public DictionaryVO getDictionaryVO() {
        return dictionaryVO;
    }

    public void setDictionaryVO(DictionaryVO dictionaryVO) {
        this.dictionaryVO = dictionaryVO;
    }
}

#### 例子
```vue
<template>
  <sra-simple-table v-loading="loading"
                    :editForm="editForm" :pageVo="pageVo" :pageParam="pageParam" :rules="rules"
                    @add="initAdd" @edit="edit" @remove="remove" @enter-search="initTable" @refresh="refresh"
                    @dialog-confirm="doUpdate" @remove-batch="removeBatch">
    <template v-slot:column>
      <el-table-column type="selection" width="55"/>
      <el-table-column prop="menuName" label="权限名称" sortable/>
      <el-table-column prop="permissionCode" label="权限编号"/>
      <el-table-column prop="sort" label="显示顺序" sortable/>
    </template>
    <!-- 表单 -->
    <template v-slot:edit>
      <el-form-item prop="menuName" label="菜单名称">
        <el-input v-model="editForm.menuName"></el-input>
      </el-form-item>
      <el-form-item prop="permissionCode" label="权限编号">
        <el-input v-model="editForm.permissionCode"></el-input>
      </el-form-item>
      <el-form-item prop="sort" label="显示顺序">
        <el-input v-model="editForm.sort" type="number"></el-input>
      </el-form-item>
    </template>
  </sra-simple-table>
</template>

<script setup lang="ts">
import SraSimpleTable from "@/components/table/simple-table/SraSimpleTable.vue";
import {onMounted, reactive, ref, watch} from "vue";
import {reqCommonFeedback, reqSuccessFeedback} from "@/api/ApiFeedback";
import {listByPage, deleteBatch, add, update} from "@/api/system/menu-api";

const initData = {
  id: '',
  menuName: '',
  sort: 1,
  permissionCode: '',
  isMenu: '1'
};

// 表单参数
const editForm = ref<MenuModel>(initData);
// 分页参数
const pageParam = ref<PageParam>({pageNo: 1, pageSize: 15, searchKey: ''});
// api返回的分页数据
const pageVo = ref<PageVO>({pageNo: 1, pageSize: 15, total: 0, records: []});
// 加载进度
const loading = ref<boolean>(true);
// 表单校验规则
const rules = reactive({
  menuName: [{required: true, min: 2, max: 30, message: '长度限制2~30', trigger: 'blur'}],
  permissionCode: [{required: true, min: 2, max: 60, message: '长度限制2~60', trigger: 'blur'}]
});

// 初始化数据
onMounted(() => {
  initTable();
});

// 监听当前页的变化
watch(
    () => pageParam.value.pageNo, () => {
      initTable();
    }
)

/**
 * 初始化编辑数据
 * @param row
 */
const edit = (row: any): void => {
  if (row) {
    editForm.value.id = row.id;
    editForm.value.permissionCode = row.permissionCode;
    editForm.value.menuName = row.menuName;
    editForm.value.sort = row.sort;
  }
}

/**
 * 初始化新增数据
 */
const initAdd = (): void => {
  editForm.value = initData;
}

/**
 * 刷新
 */
const refresh = (): void => {
  pageParam.value.pageNo = 1;
  pageParam.value.pageSize = 15;
  pageParam.value.searchKey = '';
  setTimeout(initTable, 200);
}

/**
 * 单个移除
 * @param row
 */
const remove = (row: any) => removeBatch([row.id]);

/**
 * 初始化数据
 */
const initTable = () => {
  // 渲染数据
  if (!loading.value) {
    loading.value = true;
  }
  let param = {
    pageNo: pageParam.value.pageNo,
    pageSize: pageParam.value.pageSize,
    menuVO: {isMenu: 1, menuName: pageParam.value.searchKey}
  };
  reqCommonFeedback(listByPage(param), (data: any) => {
    pageVo.value.records = data.rows;
    pageVo.value.total = data.recordCount;
    loading.value = false;
  });
}

/**
 * 更新操作
 * @param formEl 表单组件对象
 * @param callback 回调函数，调用就会关闭对话框
 */
const doUpdate = (formEl: any, callback: Function): void => {
  formEl.validate((valid: any) => {
    if (valid) {
      if (!editForm.value.id) {
        // 新增
        reqSuccessFeedback(add(editForm.value), '新增成功', () => {
          initTable();
          callback();
        });
      } else {
        // 修改
        reqSuccessFeedback(update(editForm.value), '修改成功', () => {
          initTable();
          callback();
        });
      }
    }
  });
}

/**
 * 批量删除
 * @param ids
 */
const removeBatch = (ids: string[]) => {
  reqSuccessFeedback(deleteBatch(ids), '删除成功', () => {
    initTable();
  });
}
</script>
```
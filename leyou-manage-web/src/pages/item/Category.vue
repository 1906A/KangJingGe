<template>
  <v-card>
      <v-flex xs12 sm10>
        <v-tree url="/item/category/list"
                :isEdit="isEdit"
                @handleAdd="handleAdd"
                @handleEdit="handleEdit"
                @handleDelete="handleDelete"
                @handleClick="handleClick"
        />
      </v-flex>
  </v-card>
</template>

<script>

  export default {
    name: "category",
    data() {
      return {
        isEdit:true,
      }
    },
    methods: {
      handleAdd(node) {
        console.log("add .... ");
        console.log(node);


      },
      handleEdit(node) {
        //console.log("edit... id: " + id + ", name: " + name)


        console.log("------------------------------");
        console.log(node);
        console.log("------------------------------");

        var id =node.id;
        if(id==0){
          this.$http.post("/item/category/add",node)
            .then((res)=>{
              if(res.data=="succ"){
                alert("新建成功！");
              }else if(res.data=="nosucc"){
                alert("新建失败！");
              }
               window.location.reload();
            }).catch((error)=>{
               alert("请求失败");
          })
        }else{
          this.$http.post("/item/category/update",node)
            .then((res)=>{
              if(res.data=="succ"){
                alert("修改成功！");
              }else if(res.data=="nosucc"){
                alert("修改失败！");
              }
              window.location.reload();
            }).catch((error)=>{
            alert("请求失败");
          })
        }

      },
      handleDelete(id) {
        console.log("delete ... " + id)

        this.$http.post("/item/category/deleteById",{
          id:id
        }).then((res)=>{
          if(res.data=="succ"){
            alert("删除成功！");
          }else if(res.data=="nosucc"){
            alert("删除失败！");
          }
          window.location.reload();
        }).catch((error)=>{
          alert("请求失败");
        })
      },
      handleClick(node) {
        console.log(node)
      }
    }
  };
</script>

<style scoped>

</style>

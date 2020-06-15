package ClientService;

import com.leyou.pojo.Category;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("category")
public interface CategoryService {
    @RequestMapping("findCategoryBycid")
    public Category findCategoryBycid(@RequestParam("id") Long id);


    @RequestMapping("findCategoryBycids")
    public  List<Category> findCategoryBycids(@RequestBody List<Long> cids);
}

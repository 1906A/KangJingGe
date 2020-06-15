package ClientService;

import com.leyou.pojo.Sku;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("sku")
public interface SkuService {

    @RequestMapping("list")
    public List<Sku> findSkuBySupId(@RequestParam("id") Long id);
}

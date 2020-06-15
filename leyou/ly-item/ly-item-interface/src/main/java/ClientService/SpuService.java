package ClientService;

import Bo.SpuBo;
import com.leyou.common.Page;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("spu")
public interface SpuService {

    @RequestMapping("page")
    public Page<SpuBo> findPageAll(@RequestParam("key") String key,
                                   @RequestParam("saleable") Integer saleable,
                                   @RequestParam("page") Integer page,
                                   @RequestParam("rows") Integer rows);

    @RequestMapping("detail/{spuId}")
    public SpuDetail findSpuDetailBySpuId(@PathVariable("spuId") Long spuId);

    @RequestMapping("findSpuByspuId")
    public Spu findSpuById(@RequestParam("spuId") Long spuId );


    @RequestMapping("findSpuId")
    public  Spu findSpuId(@RequestParam("spuId") Long spuId);
}

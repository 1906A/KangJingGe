package ClientService;

import com.leyou.pojo.SepcGroup;
import com.leyou.pojo.SepcParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("spec")
public interface SpecService {


    /**
     * 规格参数
     * @param cid
     * @return
     */
    @RequestMapping("paramsBycid")
    public List<SepcParam> findSepcParamAndSearching(@RequestParam(value = "cid") Long cid);


    @RequestMapping("groups/{cid}")
    public List<SepcGroup> findBySepcGroupCid(@PathVariable("cid") Long cid);


    @RequestMapping("paramsBycidAndGeneric")
    public List<SepcParam> paramsBycidAndGeneric(@RequestParam(value = "cid") Long cid);
}

package StreamDemo;

import commondata.AccountConsumeVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author bin.yu
 * @create 2018-10-15 19:49
 **/
public class GroupAndSum {

    public static void main(String[] args) {
        List<AccountConsumeVo> rs = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            AccountConsumeVo vo = new AccountConsumeVo();
            vo.setAmount(100d);
            vo.setOrgId(1L);
            vo.setPeerOrgId(2L);
            vo.setProductId(Integer.valueOf(i).longValue());
            rs.add(vo);
        }
        AccountConsumeVo vo = new AccountConsumeVo();
        vo.setAmount(100d);
        vo.setOrgId(1L);
        vo.setPeerOrgId(3L);
        vo.setProductId(Integer.valueOf(1).longValue());
        rs.add(vo);

        Map<Long, Map<Long, Double>> map = rs.stream().collect(Collectors.groupingBy(AccountConsumeVo::getOrgId,
                Collectors.groupingBy(AccountConsumeVo::getPeerOrgId, Collectors.summingDouble(AccountConsumeVo::getAmount))));
        for (Map.Entry<Long, Map<Long, Double>> entry : map.entrySet()) {
            Long orgId = entry.getKey();
            for (Map.Entry<Long, Double> doubleEntry : entry.getValue().entrySet()) {
                System.out.println(doubleEntry.getKey());
            }
        }
    }
}

package com.lzb.demo.common.joininmemory.support;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.lzb.demo.common.joininmemory.JoinItemExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;

/**
 * Created by taoli on 2022/7/31.
 * 
 * 
 *
 * 串行执行器，多个 join 操作顺序执行
 */
@Slf4j
public class SerialJoinItemsExecutor<DATA> extends AbstractJoinItemsExecutor<DATA> {
    public SerialJoinItemsExecutor(Class<DATA> dataCls,
                                   List<JoinItemExecutor<DATA>> joinItemExecutors) {
        super(dataCls, joinItemExecutors);
    }

    @Override
    public void execute(List<DATA> datas) {
        getJoinItemExecutors().forEach(dataJoinExecutor -> {
            log.debug("run join on level {} use {}",
                    dataJoinExecutor.runOnLevel(), dataJoinExecutor);
            if (log.isDebugEnabled()){
                StopWatch stopWatch = StopWatch.createStarted();
                dataJoinExecutor.execute(datas);
                stopWatch.stop();

                log.debug("run execute cost {} ms, executor is {}, data is {}.",
                        stopWatch.getTime(TimeUnit.MILLISECONDS),
                        dataJoinExecutor,
                        datas);
            }else {
                dataJoinExecutor.execute(datas);
            }

        });
    }
}

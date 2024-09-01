package com.lzb;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;

/**
 * <br/>
 * Created on : 2023-03-01 10:31
 * @author lizebin
 */
@Execution(CONCURRENT)
@ExtendWith(MockitoExtension.class)
public abstract class BaseUnitTest extends BaseTest {

}

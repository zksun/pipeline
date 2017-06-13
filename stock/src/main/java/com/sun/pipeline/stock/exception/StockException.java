package com.sun.pipeline.stock.exception;

/**
 * Created by zksun on 13/06/2017.
 */
public class StockException extends RuntimeException {

    public StockException(String message) {
        super(message);
    }

    public StockException(String message, Throwable cause) {
        super(message, cause);
    }

    public StockException(Throwable cause) {
        super(cause);
    }


}

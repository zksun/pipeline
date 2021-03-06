package com.sun.pipeline.stock.price;

import com.sun.pipeline.stock.Authority;
import com.sun.pipeline.stock.domain.Stock;
import com.sun.pipeline.stock.domain.StockPrice;
import com.sun.pipeline.stock.domain.TimeDomain;
import com.sun.pipeline.stock.exception.StockException;
import com.sun.pipeline.util.internal.io.Container;
import com.sun.pipeline.util.internal.io.ContainerAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.sun.pipeline.stock.Authority.FORWARD_ANSWER_AUTHORITY;
import static com.sun.pipeline.stock.StockUtil.calculateAuthorityPrice;
import static com.sun.pipeline.stock.StockUtil.getRealSequence;

/**
 * Created by zksun on 13/06/2017.
 */
public class StockDayContainer extends ContainerAdapter<List<StockPrice>, Object> {

    private Authority authority;

    private Stock stock;

    private LocalDate dateTime;

    private List<StockPrice> prices;

    private boolean fileCheckPassed;

    private boolean isLi = false;

    private long avgPrice = -1;

    public StockDayContainer(Stock stock, LocalDate dateTime) {
        this.stock = stock;
        this.dateTime = dateTime;
        this.authority = FORWARD_ANSWER_AUTHORITY;
    }

    public StockDayContainer(Stock stock, LocalDate dateTime, Authority authority) {
        this.stock = stock;
        this.dateTime = dateTime;
        this.authority = authority;
    }


    public LocalDate getDateTime() {
        return dateTime;
    }

    @Override
    public Container swallow(File file) {
        if (null == file || !file.exists()) {
            throw new NullPointerException("no file");
        }
        if (file.isDirectory()) {
            throw new IllegalArgumentException("file is a directory");
        }
        if (!file.canRead()) {
            throw new IllegalArgumentException("file can not read");
        }
        fileCheckPassed = checkFileNotPass(file);
        try {
            return swallow(new FileInputStream(file));
        } catch (Throwable e) {
            throw new IllegalArgumentException("file error", e);
        }
    }

    private boolean checkFileNotPass(File file) {
        String[] split = file.getName().split("\\.");
        return split[0].equals(dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
    }

    @Override
    public Container swallow(InputStream inputStream) {
        if (null == inputStream) {
            throw new NullPointerException("no stream");
        }
        if (!(inputStream instanceof FileInputStream)) {
            throw new UnsupportedOperationException();
        }
        if (!fileCheckPassed) {
            return this;
        }
        add(convert(inputStream));
        if (isLi = isLi()) {
            for (StockPrice price : this.prices) {
                price.setPrice(price.getPrice() / 10);
                price.setAuthorityPrice(calculateAuthorityPrice(stock.getStockCode(), dateTime, price.getPrice(), authority));
            }
        } else {
            for (StockPrice price : this.prices) {
                price.setAuthorityPrice(calculateAuthorityPrice(stock.getStockCode(), dateTime, price.getPrice(), authority));
            }
        }

        return this;
    }

    protected void add(List<StockPrice> data) {
        this.prices = data;
    }

    public List<StockPrice> getData() {
        return prices;
    }

    public int totalHand() {
        int total = 0;
        for (StockPrice price : prices) {
            total += price.getHand();
        }
        return total;
    }

    public long avgPrice() {
        if (this.avgPrice > 0) {
            return this.avgPrice();
        }
        double total = 0;
        for (StockPrice price : prices) {
            total += price.getAuthorityPrice();
        }
        this.avgPrice = (long) total / prices.size();
        return avgPrice;
    }


    protected List<StockPrice> convert(InputStream inputStream) {
        byte[] buf = new byte[16];
        List<StockPrice> list = new ArrayList<>();
        try {
            while ((inputStream.read(buf)) != -1) {
                StockPrice stockPrice = getStockPrice(buf);
                list.add(stockPrice);
            }
        } catch (Exception e) {
            throw new StockException("convert stock price error", e);
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        //ignore
                    }
                }
            }
        }
        return list;
    }

    public boolean isLi() {
        if (isLi) {
            return isLi;
        }
        if (!this.prices.isEmpty()) {
            for (StockPrice price : prices) {
                if (0 != (price.getPrice() % 10)) {
                    return false;
                }
            }
        }
        return true;
    }

    private StockPrice getStockPrice(byte[] buf) {
        ByteBuffer allocate = null;
        try {
            allocate = ByteBuffer.allocate(16).put(buf);
            allocate.order(ByteOrder.LITTLE_ENDIAN);
            allocate.flip();
            int realSequence = getRealSequence(allocate.getShort());
            int realPrice = allocate.getInt();
            int hand = allocate.getInt();
            allocate.getInt();
            short buyOrSell = allocate.getShort();
            StockPrice stockPrice = new StockPrice();
            stockPrice.setStock(this.stock);
            stockPrice.setTime(calculateTimeDomain(realSequence));
            stockPrice.setPrice((long) realPrice);
            stockPrice.setHand(hand);
            stockPrice.setTrade(buyOrSell);
            //
            return stockPrice;
        } catch (Exception e) {
            throw new StockException("convert stock price error", e);
        } finally {
            if (null != allocate) {
                allocate = null;
            }
        }
    }

    private TimeDomain calculateTimeDomain(int realSequence) {
        LocalDateTime localDateTime = null;
        if (realSequence <= 120) {
            LocalDateTime realDateTime = LocalDateTime.of(this.dateTime, LocalTime.of(9, 30));
            localDateTime = realDateTime.plusMinutes(realSequence);
        } else {
            LocalDateTime realDateTime = LocalDateTime.of(this.dateTime, LocalTime.of(11, 0));
            localDateTime = realDateTime.plusMinutes(realSequence);
        }
        return new TimeDomain(localDateTime);
    }

    public Stock getStock() {
        return stock;
    }
}

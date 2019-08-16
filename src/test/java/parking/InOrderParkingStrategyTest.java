package parking;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class InOrderParkingStrategyTest {

    @Test
    public void testCreateReceipt_givenACarAndAParkingLog_thenGiveAReceiptWithCarNameAndParkingLotName() {

        /* Exercise 1, Write a test case on InOrderParkingStrategy.createReceipt()
         * With using Mockito to mock the input parameter */
        Car leoCar = new Car("LeoCar");
        ParkingLot parkingLot = new ParkingLot("Parking Lot 1", 1);

        Car mLeoCar = mock(Car.class);
        when(mLeoCar.getName()).thenReturn("LeoCar2");
        ParkingLot mParkingLot = mock(ParkingLot.class);
        when(mParkingLot.getName()).thenReturn("Parking Lot A");

        InOrderParkingStrategy inOrderParkingStrategy = new InOrderParkingStrategy();

        Receipt receipt = inOrderParkingStrategy.createReceipt(mParkingLot, mLeoCar);

        verify(mLeoCar, times(1)).getName();
        assertEquals("LeoCar2", receipt.getCarName());
    }

    @Test
    public void testCreateNoSpaceReceipt_givenACar_thenGiveANoSpaceReceipt() {

        /* Exercise 1, Write a test case on InOrderParkingStrategy.createNoSpaceReceipt()
         * With using Mockito to mock the input parameter */
        Car mLeoCar = mock(Car.class);
        when(mLeoCar.getName()).thenReturn("LeoCar2");

        InOrderParkingStrategy inOrderParkingStrategy = new InOrderParkingStrategy();
        Receipt receipt = inOrderParkingStrategy.createNoSpaceReceipt(mLeoCar);

        verify(mLeoCar, times(1)).getName();
        assertEquals("LeoCar2", receipt.getCarName());
        assertEquals(ParkingStrategy.NO_PARKING_LOT, receipt.getParkingLotName());
    }

    @Test
    public void testPark_givenNoAvailableParkingLot_thenCreateNoSpaceReceipt() {

        /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for no available parking lot */
        Car leoCar = new Car("LeoCar");
//        ParkingLot parkingLot = new ParkingLot("Parking Lot 1", 1);

        InOrderParkingStrategy spyInOrderParkingStrategy = spy(new InOrderParkingStrategy());
        Receipt receipt = spyInOrderParkingStrategy.park(null, leoCar);

        verify(spyInOrderParkingStrategy).createNoSpaceReceipt(leoCar);

    }

    @Test
    public void testPark_givenThereIsOneParkingLotWithSpace_thenCreateReceipt() {

        /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for one available parking lot */
        Car leoCar = new Car("leoCar");
        ParkingLot parkingLot = new ParkingLot("Parking Lot 1", 1);
        List<ParkingLot> parkingLots = Arrays.asList(parkingLot);
        InOrderParkingStrategy spyInOrderParkingStrategy = spy(new InOrderParkingStrategy());

        Receipt receipt = spyInOrderParkingStrategy.park(parkingLots, leoCar);

        verify(spyInOrderParkingStrategy).createReceipt(parkingLot, leoCar);
        assertEquals("leoCar", receipt.getCarName());
        assertEquals("Parking Lot 1", receipt.getParkingLotName());
    }

    @Test
    public void testPark_givenThereIsOneFullParkingLot_thenCreateReceipt() {

        /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for one available parking lot but it is full */
        InOrderParkingStrategy spyInOrderParkingStrategy = spy(new InOrderParkingStrategy());
        Car leoCar = new Car("leoCar");
        ParkingLot parkingLot = new ParkingLot("Parking Lot 1", 1);
        parkingLot.getParkedCars().add(new Car("otherCar"));
        List<ParkingLot> parkingLots = Arrays.asList(parkingLot);

        Receipt receipt = spyInOrderParkingStrategy.park(parkingLots, leoCar);

        verify(spyInOrderParkingStrategy, times(0)).createReceipt(parkingLot, leoCar);
        verify(spyInOrderParkingStrategy).createNoSpaceReceipt(leoCar);
    }

    @Test
    public void testPark_givenThereIsMultipleParkingLotAndFirstOneIsFull_thenCreateReceiptWithUnfullParkingLot() {

        /* Exercise 3: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for multiple parking lot situation */
        InOrderParkingStrategy spyInOrderParkingStrategy = spy(new InOrderParkingStrategy());
        Car leoCar = new Car("leoCar");
        ParkingLot parkingLot1 = new ParkingLot("Parking Lot 1", 1);
        parkingLot1.getParkedCars().add(new Car("otherCar"));
        ParkingLot parkingLot2 = new ParkingLot("Parking Lot 2", 1);
        List<ParkingLot> parkingLots = Arrays.asList(parkingLot1, parkingLot2);

        Receipt receipt = spyInOrderParkingStrategy.park(parkingLots, leoCar);

        verify(spyInOrderParkingStrategy).createReceipt(parkingLot2, leoCar);
        assertEquals("leoCar", receipt.getCarName());
        assertEquals("Parking Lot 2", receipt.getParkingLotName());
    }


}

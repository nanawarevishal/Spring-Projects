package com.bookcab.cabbooking.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookcab.cabbooking.Config.JwtProvider;
import com.bookcab.cabbooking.Domain.RideStatus;
import com.bookcab.cabbooking.Domain.UserRole;
import com.bookcab.cabbooking.Exception.DriverException;
import com.bookcab.cabbooking.Model.Driver;
import com.bookcab.cabbooking.Model.License;
import com.bookcab.cabbooking.Model.Ride;
import com.bookcab.cabbooking.Model.Vehicle;
import com.bookcab.cabbooking.Repository.DriverRepository;
import com.bookcab.cabbooking.Repository.LicenseRepository;
import com.bookcab.cabbooking.Repository.RideRepository;
import com.bookcab.cabbooking.Repository.VehicleRepository;
import com.bookcab.cabbooking.Request.DriverSignUpRequest;


@Service
public class DriverServiceImpl implements DriverService {

    private DriverRepository driverRepository;

    private PasswordEncoder passwordEncoder;

    private Calculators distanceCalculator;

    private VehicleRepository vehicleRepository;

    private LicenseRepository licenseRepository;

    private RideRepository rideRepository;

    public DriverServiceImpl(DriverRepository driverRepository,PasswordEncoder passwordEncoder,VehicleRepository vehicleRepository,LicenseRepository licenseRepository,RideRepository rideRepository,Calculators distanceCalculator){
        this.driverRepository = driverRepository;
        this.passwordEncoder = passwordEncoder;
        this.vehicleRepository = vehicleRepository;
        this.licenseRepository = licenseRepository;
        this.rideRepository = rideRepository;
        this.distanceCalculator = distanceCalculator;
    }

    @Override
    public Driver registerDriver(DriverSignUpRequest request) {

        License license = request.getLicense();
        Vehicle vehicle = request.getVehicle();

        License createLicence = new License();
        createLicence.setLicenseState(license.getLicenseState());
        createLicence.setLicenseNumber(license.getLicenseNumber());
        createLicence.setLicenseExpirationDate(license.getLicenseExpirationDate());
        createLicence.setId(license.getId());
        
        License savedLicense = licenseRepository.save(createLicence);
        
        Vehicle createVehilce = new Vehicle();
        createVehilce.setCapacity(vehicle.getCapacity());
        createVehilce.setColor(vehicle.getColor());
        createVehilce.setId(vehicle.getId());
        createVehilce.setLicensePlate(vehicle.getLicensePlate());
        createVehilce.setMake(vehicle.getMake());
        createVehilce.setModel(vehicle.getModel());
        createVehilce.setYear(vehicle.getYear());

        Vehicle savedVehicle = vehicleRepository.save(createVehilce);
        
        Driver driver = new Driver();
        
        driver.setEmail(request.getEmail());
        driver.setMobile(request.getMobile());
        driver.setFirstName(request.getFirstName());
        driver.setLastName(request.getLastName());
        driver.setPassword(passwordEncoder.encode(request.getPassword()));
        driver.setLicense(savedLicense);
        driver.setVehicle(savedVehicle);
        driver.setRole(UserRole.DRIVER);
        driver.setLongitude(request.getLongitude());
        driver.setLatitude(request.getLatitude());

        Driver savedDriver = driverRepository.save(driver);

        savedLicense.setDriver(savedDriver);
        savedVehicle.setDriver(savedDriver);

        licenseRepository.save(savedLicense);
        vehicleRepository.save(savedVehicle);

        return savedDriver;

        
    }

    @Override
    public List<Driver> getAvailableDrivers(double pickupLatitude, double pickupLongitude, Ride ride) {
        
        List<Driver>allDrivers = driverRepository.findAll();

        List<Driver>availableDrivers = new ArrayList<>();

        for(Driver driver : allDrivers){

            if(driver.getCurrentRide()!=null && driver.getCurrentRide().getStatus()!=RideStatus.COMPLETED){
                continue;
            }

            if(ride.getDeclinedDriver().contains(driver.getId())){
                System.out.println("Its containes");
                continue;
            }

            // double driverLatitude = driver.getLatitude();
            // double driverLongitude = driver.getLongitude();

            // double distance = distanceCalculator.calculateDistance(driverLatitude, driverLongitude, pickupLatitude, pickupLongitude);

            availableDrivers.add(driver);

        }

        return availableDrivers;
    }

    @Override
    public Driver findNearestDriver(List<Driver> availableDrivers, double pickupLatitude, double pickupLongitude) {
        
        double minDistance = Double.MAX_VALUE;

        Driver nearestDriver = null;

        for(Driver driver : availableDrivers){

            double driverLatitude = driver.getLatitude();
            double driverLongitude = driver.getLongitude();

            double distance = distanceCalculator.calculateDistance(pickupLatitude, pickupLongitude, driverLatitude, driverLongitude);

            if(minDistance > distance){
                minDistance = distance;
                nearestDriver = driver;
            }
            
        }

        return nearestDriver;
    }

    @Override
    public Driver getRequestDriverProfile(String jwt) {
        
        String email = JwtProvider.getEmailFromJwtToken(jwt);
        
        Driver driver = driverRepository.findByEmail(email);
        if(driver == null){
            throw new DriverException("Driver doesn't exist with email: "+email);
        }

        return driver;

    }

    @Override
    public Ride getDriverCurrentRide(Long driverId) {
        
        Driver driver = findDriverById(driverId);

        return driver.getCurrentRide();
    }

    @Override
    public List<Ride> getAllocatedRide(Long driverId) {
        Driver driver = findDriverById(driverId);

        List<Ride>rides = driverRepository.getAllocatedRides(driverId);
        
        return rides;
    }

    @Override
    public Driver findDriverById(Long driverId) {
        
        Optional<Driver>opt = driverRepository.findById(driverId);

        if(opt.isPresent()){
            return opt.get();
        }

        throw new DriverException("Drive doesn't exist with id: "+driverId);
    }

    @Override
    public List<Ride> getCompletedRide(Long driverId) {
        
        Driver driver = findDriverById(driverId);

        List<Ride>rides = driverRepository.getCompletedRides(driverId);

        return rides;
    }
    
}

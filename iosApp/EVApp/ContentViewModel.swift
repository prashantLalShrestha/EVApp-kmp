import Foundation
import EVLib

class ContentViewModel: ObservableObject {
    
    let evStationRepo = EVLibDI.shared.evStationRepository
    
    func getStations() async {
        do {
            print("before")
            try await evStationRepo.getNearbyEVStations(latitude: 20.3249284923, longitude: 50.3204023940)
            print("after")
        }
        catch {
            print("exception \(error.localizedDescription)")
        }
    }
}

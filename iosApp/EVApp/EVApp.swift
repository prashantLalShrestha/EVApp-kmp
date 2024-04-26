//
// 
//

import SwiftUI
import EVLib

@main
struct EVApp: App {
    
    
    init() {
        KoinKt.doInitKoin()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

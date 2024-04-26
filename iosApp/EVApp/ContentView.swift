//
// 
//

import SwiftUI
import EVLib

struct ContentView: View {
    
    @StateObject
    private var viewModel = ContentViewModel()
    
    var body: some View {
        VStack {
            Image(systemName: "globe")
                .imageScale(.large)
                .foregroundStyle(.tint)
            Text("Greeting: \(EVLib.getPlatform().name)")
        }
        .padding()
        .task {
            await viewModel.getStations()
        }
    }
}

#Preview {
    ContentView()
}

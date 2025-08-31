import { Eureka } from 'eureka-js-client'

const client = new Eureka({
   instance: {
      app: 'AUTH-SERVICE',
      hostName: 'localhost',
      ipAddr: '127.0.0.1',
      port: {
         $: 3000,
         '@enabled': true,
      },
      vipAddress: 'AUTH-SERVICE',
      dataCenterInfo: {
         '@class': 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo',
         name: 'MyOwn',
      },
   },
   eureka: {
      host: 'localhost',
      port: 8761,
      servicePath: '/eureka/apps/',
   },
})

export function registerEureka() {
   client.start(error => {
      console.log(error || 'Eureka registration complete')
   })
}

dataSource {
    pooled = true
       driverClassName = "com.mysql.jdbc.Driver"
       username = "root"
       password = "123456"
       dialect = org.hibernate.dialect.MySQL5InnoDBDialect
       properties {
          maxActive = 50
          maxIdle = 25
          minIdle = 5
          initialSize = 5
          minEvictableIdleTimeMillis = 1800000
          timeBetweenEvictionRunsMillis = 1800000
          maxWait = 10000
          ValidationQuery = 'select 1'
       }
}

hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'org.hibernate.cache.ehcache.EhCacheRegionFactory'
}

environments {
    development {
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:mysql://localhost:3307/teste_security"
        }
    }
    test {
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:mysql://localhost:3307/teste_security"
        }
    }
    production {
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:mysql://localhost:3307/teste_security"
        }
    }
}

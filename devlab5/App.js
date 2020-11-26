import React, { useState,Component } from 'react';
import { Text,StyleSheet,Button,TextInput,View,FlatList,StatusBar,TouchableOpacity,Image } from 'react-native';

//Класс карточек
class card {
  constructor(id,name,ava) {
    this.id = id;
    this.name = name;
    this.ava = ava;
  }
}
//Создаем список карточек
let cardlist = [];

let info = [];



class App extends Component {

  getMoviesFromApi = (tst) => {
    return fetch('https://api.vk.com/method/users.getFollowers?user_id='+tst+'&v=5.89&fields=photo_50%2Cfirst_name&access_token=8a4ae14f8a4ae14f8a4ae14f7c8a3e789388a4a8a4ae14fd5dc09b976dad69975ce5671')
      .then((response) => response.json())
      .then((json) => {
        return json.response.items;
      })
      .catch((error) => {
        console.error(error);
      });
  };

  state = {
    field: '',
    refresh: true
  }
 handleField = (text) => {
    this.setState({ field: text })
 }
 handleRefresh = (refresh) => {
  this.setState({refresh: refresh})
}
 getAnswer = (field) => {
  this.getMoviesFromApi(field).then((items) => {
    for(let item of items) {
      let temp = new card(item.id,item.first_name,item.photo_50)
      cardlist.push(temp)
    }
    this.handleRefresh(!this.state.refresh)
  })
}

  render() {
    const renderItem = ({ item }) => (
      <TouchableOpacity style={styles.item} onPress = {() => {
        console.log(item.id)
        cardlist = []
        this.handleRefresh(!this.state.refresh)
        info.push(item.id,item.name,item.ava)
        console.log(info)
      }}>
        <Image source={{uri:item.ava}} style={{width:40,height:40}}></Image>
        <Text style={{color:"#fff",lineHeight:40,marginLeft:40}}>{item.name}</Text>
      </TouchableOpacity>
    );
    return (
      <View style={styles.main}>
        <View style={styles.linear}>
          <TextInput 
          style={styles.input}
          onChangeText={this.handleField}
          placeholder={"Введите id пользователя"}
          />
          <Button 
          style={styles.searchButton}
          title="GO"
          color="#2EC4B6"
          onPress = {() => {
            console.log(this.state.refresh)
            cardlist = []
            info = []
            this.getAnswer(this.state.field)
            console.log(this.state.refresh)
          }
         }/>
        </View>
        <View style={{flex:1,justifyContent: "center",alignItems:"center"}}>
          <Image style={{width:140,height:140,marginTop:220,marginBottom:20}} source={{uri:info[2]}}></Image>
        <Text style={{textAlign: "center"}}>{info[1]}</Text>
        <Text style={{textAlign: "center"}}>{info[0]}</Text>
        </View>
        <FlatList style={ styles.list}
        data={cardlist}
        renderItem={renderItem}
        keyExtractor={item => item.id}
      />


      </View>
    );
  }
}

export default App;


//Cтили
const styles = StyleSheet.create({
  main: {
    marginTop: StatusBar.currentHeight,
    paddingTop:20
  },
  linear: {
    flexDirection: 'row',
  },
  input:{
    borderWidth: 1,
    borderColor: "#000",
    width:250,
    paddingLeft:20,
    paddingRight:20,
    marginLeft:20,
    marginRight:20,
  },
  list: {
    paddingBottom:20,
  },
  item: {
    flexDirection: 'row',
    padding:20,
    backgroundColor: '#969A97',
    margin: 10,
    color: "#fff"
  },

})